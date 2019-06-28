<?php

require_once 'ItemManager.php';

if($_SERVER['REQUEST_METHOD'] == 'POST') {

    if(isset($_POST['tag'])) {

        $db = new ItemManager();

        if($_POST['tag'] == 'add') {

            if(isset($_POST['name'], $_POST['quantityDesired'], $_POST['quantityGot'], $_POST['status'], $_POST['idList'])) {

                if(!$db->exist($_POST['name'], $_POST['idList'])) {

                    if($_POST['quantityDesired'] > 0) {

                        $id = $db->add($_POST['name'], $_POST['quantityDesired'], $_POST['quantityGot'], $_POST['status'], $_POST['idList']);

                        if($id != 0) {

                            $response['error'] = false;
                            $response['id'] = $id;

                        } else {

                            $response['error'] = true;
                            $response['message'] = "Adding item failed ";

                        }

                    } else {

                        $response['error'] = true;
                        $response['message'] = "Need positive quantity";

                    }

                } else {

                    $response['error'] = true;
                    $response['message'] = "Name already use for this list";

                }

            } else {

                $response['error'] = true;
                $response['message'] = "Missing parameter";

            }

        } else if($_POST['tag'] == 'get') {



        } else if($_POST['tag'] == 'getAll') {

            if(isset($_POST['idList'])) {

                $response['error'] = false;
                $response['items'] = $db->getAll($_POST['idList']);

            } else {

                $response['error'] = true;
                $response['message'] = "Missing parameter";

            }

        } else if($_POST['tag'] == 'update') {

            if(isset($_POST['id'], $_POST['name'], $_POST['quantityDesired'], $_POST['quantityGot'], $_POST['status'], $_POST['idList'])) {

                if($db->update($_POST['id'], $_POST['name'], $_POST['quantityDesired'], $_POST['quantityGot'], $_POST['status'], $_POST['idList'])) {

                    $response['error'] = false;

                } else {

                    $response['error'] = true;
                    $response['message'] = "Update item failed";

                }

            } else {

                $response['error'] = true;
                $response['message'] = "Missing parameter";

            }

        } else if($_POST['tag'] == 'delete') {

            if(isset($_POST['id'])) {

                if($db->delete($_POST['id'])) {

                    $response['error'] = false;

                } else {

                    $response['error'] = true;
                    $response['message'] = "Delete item failed";

                }

            } else {

                $response['error'] = true;
                $response['message'] = "Missing parameter";

            }

        } else if($_POST['tag'] == 'deleteAllList') {

            if(isset($_POST['idList'])) {

                if($db->deleteAllList($_POST['idList'])) {

                    $response['error'] = false;

                } else {

                    $response['error'] = true;
                    $response['message'] = "Delete items list failed";

                }

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