<?php

require_once 'ListManager.php';

if($_SERVER['REQUEST_METHOD'] == 'POST') {

    if(isset($_POST['tag'])) {

        $db = new ListManager();

        if($_POST['tag'] == 'add') {

            if(isset($_POST['name'], $_POST['spent'], $_POST['status'] ,$_POST['idUser'])) {

                $id = $db->add($_POST['name'], $_POST['spent'], $_POST['status'] ,$_POST['idUser']);

                if($id != 0) {

                    $response['error'] = false;
                    $response['id'] = $id;

                } else {

                    $response['error'] = true;
                    $response['message'] = "Adding list failed ";

                }

            } else {

                $response['error'] = true;
                $response['message'] = "Missing parameter";

            }

        } else if($_POST['tag'] == 'get') {

        	if(isset($_POST['id'])) {

                $response['error'] = false;
                $response['list'] = $db->get($_POST['id']);

			} else {

                $response['error'] = true;
                $response['message'] = "Missing parameter";

			}

        } else if($_POST['tag'] == 'getAll') {

            if(isset($_POST['idUser'])) {

                $response['error'] = false;
                $response['lists'] = $db->getAll($_POST['idUser']);

            } else {

                $response['error'] = true;
                $response['message'] = "Missing parameter";

            }

        } else if($_POST['tag'] == 'update') {

            if(isset($_POST['id'], $_POST['name'], $_POST['spent'], $_POST['status'] ,$_POST['idUser'])) {

                if($db->update($_POST['id'], $_POST['name'], $_POST['spent'], $_POST['status'] ,$_POST['idUser'])) {

                    $response['error'] = false;

                } else {

                    $response['error'] = true;
                    $response['message'] = "Update list failed";

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
                    $response['message'] = "Delete list failed";

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