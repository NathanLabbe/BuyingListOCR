<?php

class ProductManager {

	private $con;

	function __construct() {

		require_once dirname(__FILE__).'/DbConnect.php';

		$db = new DbConnect();

		$this->con = $db->connect();

	}


	function getAll($idShop) {

	        $stmt = $this->con->prepare("SELECT * FROM Product WHERE idShop = ? ORDER BY id;");

        	$stmt->bind_param("i", $idShop);

        	$stmt->execute();

	        $result = $stmt->get_result();

	        $products = array();

        	while($row = $result->fetch_assoc()) {

            		$products[] = $row;

        	}

        	return $products;

	}


}
