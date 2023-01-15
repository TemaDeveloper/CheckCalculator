<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    public $sql;
    protected $servername;
    protected $name;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->name = $dbc->name;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->name, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        $this->connect = mysqli_connect($this->servername, $this->name, $this->password, $this->databasename);
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $email, $password)
    {
        $this->connect = mysqli_connect($this->servername, $this->name, $this->password, $this->databasename);
        $name = $this->prepareData($email);
        $password = $this->prepareData($password);
        $this->sql = "SELECT * FROM " . $table . " WHERE email = '" . $email . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['email'];
            $dbpassword = $row['password'];
            if ($dbusername == $name && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function signUp($table, $name, $country, $password, $email, $about)
    {
        $name = $this->prepareData($name);
        $country = $this->prepareData($country);
        $password = $this->prepareData($password);
        $email = $this->prepareData($email);
        $password = $this->prepareData($password);
        $about = $this->prepareData($about);
        $this->connect = mysqli_connect($this->servername, $this->name, $this->password, $this->databasename);
        $this->sql =
            "INSERT INTO " . $table . " (name, country, password, email, about) VALUES ('" . $name . "','" . $country . "','" . $password . "','" . $email . "','" . $about . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

}

?>
