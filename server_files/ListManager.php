<?php

class ListManager {

	private $con;

	function __construct() {

		require_once dirname(__FILE__).'/DbConnect.php';

		$db = new DbConnect();

		$this->con = $db->connect();

	}

	function add($name, $spent, $status, $idUser) {

        $stmt = $this->con->prepare("INSERT INTO `List` (`id`, `name`, `spent`, `status`, `idUser`) VALUES(NULL, ?, ?, ?, ?);");

        $stmt->bind_param("sdii", $name, $spent, $status, $idUser);

        $stmt->execute();

        return $stmt->insert_id;

    }

    function get($id) {

        $stmt = $this->con->prepare("SELECT * FROM List WHERE id = ?;");

        $stmt->bind_param("i", $id);

        $stmt->execute();

        $list = $stmt->get_result()->fetch_assoc();

        return $list;

    }

	function getAll($idUser) {

        $stmt = $this->con->prepare("SELECT * FROM List WHERE idUser = ?;");

        $stmt->bind_param("i", $idUser);

        $stmt->execute();

        $result = $stmt->get_result();

        $lists = array();

        while($row = $result->fetch_assoc()) {

            $lists[] = $row;

        }

        return $lists;

	}

    function update($id, $name, $spent, $status, $idUser) {

        $stmt = $this->con->prepare("UPDATE `List` SET name = ?, spent = ?, status = ?, idUser = ? WHERE id = ?;");

        $stmt->bind_param("sdiii", $name, $spent, $status, $idUser, $id);

        if($stmt->execute()) {

            return true;

        } else {

            return false;

        }

    }

    function delete($id) {

        $stmt = $this->con->prepare("DELETE FROM `List` WHERE id = ?;");

        $stmt->bind_param("i", $id);

        if($stmt->execute()) {

            return true;

        } else {

            return false;

        }

    }

}