<?php

class CorrespondenceManager {

	private $con;

	function __construct() {

		require_once dirname(__FILE__).'/DbConnect.php';

		$db = new DbConnect();

		$this->con = $db->connect();

	}


	function getAll($idShop) {

	        $stmt = $this->con->prepare("SELECT DISTINCT id, name, idProduct FROM Correspondence INNER JOIN Link ON Correspondence.id = Link.idCorrespondence WHERE Link.idProduct in (SELECT id FROM Product WHERE idShop = ?) ORDER BY Link.idProduct;");

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

