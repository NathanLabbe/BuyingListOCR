<?php

require_once 'UserManager.php';

if($_SERVER['REQUEST_METHOD'] == 'POST') {

    if(isset($_POST['tag'])) {

        $db = new UserManager();

        /*** ***** ***/
        /*** Login ***/
        /*** ***** ***/

        if ($_POST['tag'] == 'login') {

            if (isset($_POST['login'], $_POST['password'])) {

                if ($db->exist($_POST['login'])) {

                    if ($db->login($_POST['login'], $_POST['password'])) {

                        $response['error'] = false;
                        $response['message'] = "login successful";

                        $user = $db->get($_POST['login']);

                        $response['id'] = $user['id'];
                        $response['login'] = $user['login'];
                        $response['mail'] = $user['mail'];

                    } else {

                        $response['error'] = true;
                        $response['message'] = "login or password invalid";

                    }

                } else {

                    $response['error'] = true;
                    $response['message'] = "login or password invalid";

                }

            } else {

                $response['error'] = true;
                $response['message'] = "Missing parameters";

            }

        /*** ******** ***/
        /*** Register ***/
        /*** ******** ***/

        } else if($_POST['tag'] == 'register') {

            if(isset($_POST['login'], $_POST['password'], $_POST['confirmPassword'], $_POST['mail'])) {

                if(!$db->exist($_POST['login'])) {

                    if($_POST['password'] == $_POST['confirmPassword']) {

                        if(preg_match(" /^[^\W][a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\@[a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\.[a-zA-Z]{2,4}$/ ", $_POST['mail'])) {

                            if($db->register($_POST['login'], $_POST['password'], $_POST['mail'])) {

                                $response['error'] = false;
                                $response['message'] = "register successful";

                                $user = $db->get($_POST['login']);

                                $response['id'] = $user['id'];
                                $response['login'] = $user['login'];
                                $response['mail'] = $user['mail'];

                            } else {

                                $response['error'] = true;
                                $response['message'] = "An error is occurred";

                            }

                        } else {

                            $response['error'] = true;
                            $response['message'] = "Please enter a valid email";

                        }

                    } else {

                        $response['error'] = true;
                        $response['message'] = "Two password doesn't correspond";

                    }

                } else {

                    $response['error'] = true;
                    $response['message'] = "Login already use";

                }

            } else {

                $response['error'] = true;
                $response['message'] = "Missing parameters";

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
