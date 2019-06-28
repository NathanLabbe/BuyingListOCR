<?php

class ItemManager {

    private $con;

    function __construct() {

        require_once dirname(__FILE__).'/DbConnect.php';

        $db = new DbConnect();

        $this->con = $db->connect();

    }

    function add($name, $quantityDesired, $quantityGot, $status, $idList) {

        $stmt = $this->con->prepare("INSERT INTO `Item` (`id`, `name`, `quantityDesired`, `quantityGot`, `status`, `idList`) VALUES(NULL, ?, ?, ?, ?, ?);");

        $stmt->bind_param("siiii", $name, $quantityDesired, $quantityGot, $status, $idList);

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

    function getAll($idList) {

        $stmt = $this->con->prepare("SELECT * FROM Item WHERE idList = ?;");

        $stmt->bind_param("i", $idList);

        $stmt->execute();

        $result = $stmt->get_result();

        $items = array();

        while($row = $result->fetch_assoc()) {

            $items[] = $row;

        }

        return $items;

    }

    function update($id, $name, $quantityDesired, $quantityGot, $status, $idList) {

        $stmt = $this->con->prepare("UPDATE `Item` SET name = ?, quantityDesired = ?, quantityGot = ?, status = ?, idList = ? WHERE id = ?;");

        $stmt->bind_param("siiiii", $name, $quantityDesired, $quantityGot, $status, $idList, $id);

        if($stmt->execute()) {

            return true;

        } else {

            return false;

        }

    }

    function delete($id) {

        $stmt = $this->con->prepare("DELETE FROM `Item` WHERE id = ?;");

        $stmt->bind_param("i", $id);

        if($stmt->execute()) {

            return true;

        } else {

            return false;

        }

    }

    function deleteAllList($idList) {

        $stmt = $this->con->prepare("DELETE FROM `Item` WHERE idList = ?;");

        $stmt->bind_param("i", $idList);

        if($stmt->execute()) {

            return true;

        } else {

            return false;

        }

    }

    function exist($name, $idList) {

        $stmt = $this->con->prepare("SELECT * FROM Item WHERE name = ? AND idList = ?;");

        $stmt->bind_param("si", $name, $idList);

        $stmt->execute();

        if($stmt->fetch() == null) {

            return false;

        } else {

            return true;

        }

    }

}