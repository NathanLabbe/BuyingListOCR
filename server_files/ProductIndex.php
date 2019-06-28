<?php

require_once 'ProductManager.php';
require_once 'CorrespondenceManager.php';

if($_SERVER['REQUEST_METHOD'] == 'POST') {

    if(isset($_POST['tag'])) {

        $dbProduct = new ProductManager();
	$dbCorrespondence = new CorrespondenceManager();

        if($_POST['tag'] == 'getAllProductAndCorrespondence') {

            if(isset($_POST['idShop'])) {

                $response['error'] = false;
                $response['products'] = $dbProduct->getAll($_POST['idShop']);
		        $response['correspondences'] = $dbCorrespondence->getAll($_POST['idShop']);

            } else {

                $response['error'] = true;
                $response['message'] = "Missing parameter";

            }

        } else {

            $response['error'] = true;
            $response['message'] = "Invalid tag";

        }

    } else {

        $response['error'] = true;
        $response['message'] = "Missing tag";

    }

} else {

    $response['error'] = true;
    $response['message'] = "Invalid request";

}

echo json_encode($response);
