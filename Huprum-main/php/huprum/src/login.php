<?php
function Login($pdo, $data)
{
    if (isset($data["phone"])) {
        $stmt = $pdo->prepare("SELECT * FROM users WHERE phone = ? LIMIT 1");
        $res = $stmt->execute(array($data["phone"]));
    } else if (isset($data["email"])) {
        $stmt = $pdo->prepare("SELECT * FROM users WHERE email = ? LIMIT 1");
        $res = $stmt->execute(array($data["email"]));
    } else if (isset($data["login"])) {
        $stmt = $pdo->prepare("SELECT * FROM users WHERE login = ? LIMIT 1");
        $res = $stmt->execute(array($data["login"]));
    } else return json_encode(array('status' => 0, 'msg' => 'No login, email or phone'), JSON_UNESCAPED_UNICODE);
    $row = $stmt->fetch();
    if (isset($row['id'])) {
        $row['status'] = 0;
        $row['msg'] = "ok";
    } else {
        $row['status'] = 1;
        $row['msg'] = "no user";
    }
    return json_encode($row, JSON_UNESCAPED_UNICODE);
}
