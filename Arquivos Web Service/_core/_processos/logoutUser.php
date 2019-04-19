<?php
require "../../_core/_config/functions.php";
$con = new Controller();

session_start();
$_SESSION = array();
session_destroy();