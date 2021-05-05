<?php

/****************************************************************
 * сервер huprum										*
 * 04.05.2021													*
 * Автор: Янчук А (yaa)											*
 *****************************************************************/
header("Access-Control-Allow-Origin: *");
require_once('src/db.php');
require_once("src/debugOutput.php");
require_once("src/login.php");

$debug = new debugOutput('server.txt');

$postData = file_get_contents('php://input');
//$data = parse_str($postData);
$data = json_decode($postData, true);

$debug->put($data, '$data');

if (isset($data['action'])) {
    $pdo = new db();
    switch ($data['action']) {
        case 'login':
            print Login($pdo, $data);
            return;
    }
}
print json_encode(array("status" => 0, "msg" => "Bad request"));
