<?php

class UserManager {

	private $con;

	function __construct() {

		require_once dirname(__FILE__).'/DbConnect.php';

		$db = new DbConnect();

		$this->con = $db->connect();

	}

	function get($login) {

        $stmt = $this->con->prepare("SELECT * FROM User WHERE login = ?;");

        $stmt->bind_param("s", $login);

        $stmt->execute();

        return $stmt->get_result()->fetch_assoc();

    }

	function login($login, $password) {

	    $stmt = $this->con->prepare("SELECT password FROM User WHERE login = ?;");

	    $stmt->bind_param("s", $login);

	    $stmt->execute();

        $stmt->bind_result($hashPassword);

	    $stmt->fetch();

        if(password_verify($password, $hashPassword)) {

            return true;

        } else {

            return false;

        }

    }

    function register($login, $password, $mail) {

        $hashPassword = password_hash($password, PASSWORD_DEFAULT);

        $stmt = $this->con->prepare("INSERT INTO `User` (`id`, `login`, `password`, `mail`) VALUES (NULL, ?, ?, ?);");

        $stmt->bind_param("sss", $login, $hashPassword, $mail);

        if($stmt->execute()) {

            return true;

        } else {

            return false;

        }

    }

    function exist($login) {

        $stmt = $this->con->prepare("SELECT * FROM User WHERE login = ?;");

        $stmt->bind_param("s", $login);

        $stmt->execute();

        if($stmt->fetch() == null) {

            return false;

        } else {

            return true;

        }

    }

}
