DbHandler.php
<?php
 
/**
 * Class to handle all db operations
 * This class will have CRUD methods for database tables
 */
class DbHandler {
 
    private $conn;
 
    function __construct() {
        require_once dirname(__FILE__) . './DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }
 
    /* ------------- `users` table method ------------------ */
 
    /**
     * Creating new user
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function createUser($name, $email, $password) {
        require_once 'PassHash.php';
        $response = array();
 
        // First check if user already existed in db
        if (!$this->isUserExists($email)) {
            // Generating password hash
            $password_hash = PassHash::hash($password);
 
            // Generating API key
            $api_key = $this->generateApiKey();
 
            // insert query
            $stmt = $this->conn->prepare("INSERT INTO users(name, email, password_hash, api_key, status) values(?, ?, ?, ?, 1)");
            $stmt->bind_param("ssss", $name, $email, $password_hash, $api_key);
 
            $result = $stmt->execute();
 
            $stmt->close();
 
            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        } else {
            // User with same email already existed in the db
            return USER_ALREADY_EXISTED;
        }
 
        return $response;
    }
 
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public function checkLogin($email, $password) {
        // fetching user by email
        $stmt = $this->conn->prepare("SELECT password_hash FROM users WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        $stmt->execute();
 
        $stmt->bind_result($password_hash);
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // Found user with the email
            // Now verify the password
 
            $stmt->fetch();
 
            $stmt->close();
 
            if (PassHash::check_password($password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                return FALSE;
            }
        } else {
            $stmt->close();
 
            // user not existed with the email
            return FALSE;
        }
    }
 
    /**
     * Checking for duplicate user by email address
     * @param String $email email to check in db
     * @return boolean
     */
    private function isUserExists($email) {
        $stmt = $this->conn->prepare("SELECT id from users WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
 
    /**
     * Fetching user by email
     * @param String $email User email id
     */
    public function getUserByEmail($email) {
        $stmt = $this->conn->prepare("SELECT name, email, api_key, status, created_at FROM users WHERE email = ?");
        $stmt->bind_param("s", $email);
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
 
    /**
     * Fetching user api key
     * @param String $user_id user id primary key in user table
     */
    public function getApiKeyById($user_id) {
        $stmt = $this->conn->prepare("SELECT api_key FROM users WHERE id = ?");
        $stmt->bind_param("i", $user_id);
        if ($stmt->execute()) {
            $api_key = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $api_key;
        } else {
            return NULL;
        }
    }
 
    /**
     * Fetching user id by api key
     * @param String $api_key user api key
     */
    public function getUserId($api_key) {
        $stmt = $this->conn->prepare("SELECT id FROM users WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        if ($stmt->execute()) {
            $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user_id;
        } else {
            return NULL;
        }
    }
 
    /**
     * Validating user api key
     * If the api key is there in db, it is a valid key
     * @param String $api_key user api key
     * @return boolean
     */
    public function isValidApiKey($api_key) {
        $stmt = $this->conn->prepare("SELECT id from users WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
 
    /**
     * Generating random Unique MD5 String for user Api key
     */
    private function generateApiKey() {
        return md5(uniqid(rand(), true));
    }
 
    /* ------------- `orders` table method ------------------ */
 
    /**
     * Creating new order
     * @param String $user_id user id to whom order belongs to
     * @param String $order order text
     */
    public function createorder($user_id, $order) {        
        $stmt = $this->conn->prepare("INSERT INTO orders(order) VALUES(?)");
        $stmt->bind_param("s", $order);
        $result = $stmt->execute();
        $stmt->close();
 
        if ($result) {
            // order row created
            // now assign the order to user
            $new_order_id = $this->conn->insert_id;
            $res = $this->createUserorder($user_id, $new_order_id);
            if ($res) {
                // order created successfully
                return $new_order_id;
            } else {
                // order failed to create
                return NULL;
            }
        } else {
            // order failed to create
            return NULL;
        }
    }
 
    /**
     * Fetching single order
     * @param String $order_id id of the order
     */
    public function getorder($order_id, $user_id) {
        $stmt = $this->conn->prepare("SELECT t.id, t.order, t.status, t.created_at from orders t, user_orders ut WHERE t.id = ? AND ut.order_id = t.id AND ut.user_id = ?");
        $stmt->bind_param("ii", $order_id, $user_id);
        if ($stmt->execute()) {
            $order = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $order;
        } else {
            return NULL;
        }
    }
 
    /**
     * Fetching all user orders
     * @param String $user_id id of the user
     */
    public function getAllUserorders($user_id) {
        $stmt = $this->conn->prepare("SELECT t.* FROM orders t, user_orders ut WHERE t.id = ut.order_id AND ut.user_id = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $orders = $stmt->get_result();
        $stmt->close();
        return $orders;
    }
 
    /**
     * Updating order
     * @param String $order_id id of the order
     * @param String $order order text
     * @param String $status order status
     */
    public function updateorder($user_id, $order_id, $order, $status) {
        $stmt = $this->conn->prepare("UPDATE orders t, user_orders ut set t.order = ?, t.status = ? WHERE t.id = ? AND t.id = ut.order_id AND ut.user_id = ?");
        $stmt->bind_param("siii", $order, $status, $order_id, $user_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }
 
    /**
     * Deleting a order
     * @param String $order_id id of the order to delete
     */
    public function deleteorder($user_id, $order_id) {
        $stmt = $this->conn->prepare("DELETE t FROM orders t, user_orders ut WHERE t.id = ? AND ut.order_id = t.id AND ut.user_id = ?");
        $stmt->bind_param("ii", $order_id, $user_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }
 
    /* ------------- `user_orders` table method ------------------ */
 
    /**
     * Function to assign a order to user
     * @param String $user_id id of the user
     * @param String $order_id id of the order
     */
    public function createUserorder($user_id, $order_id) {
        $stmt = $this->conn->prepare("INSERT INTO user_orders(user_id, order_id) values(?, ?)");
        $stmt->bind_param("ii", $user_id, $order_id);
        $result = $stmt->execute();
        $stmt->close();
        return $result;
    }
 
}
 
?>