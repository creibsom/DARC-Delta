<?php

/**
* Author: Cody Reibsome (creibsom@mail.umw.edu)
* Last Updated: 1/18/2014
* Description: Uses Abraham's TwitterOAuth Library to:
*	1) Query a user_id from a given screen_name
*	2) Depending on numQueries: Query a list of user_id's of the user's friends and followers (numQueries > 0)
*	3) If numQueries > 1, query all of the previously found user_id's for their friends and followers
* Usage: ./findFollowerRelations.php CONSUMER_KEY CONSUMER_SECRET OAUTH_TOKEN OAUTH_TOKEN_SECRET screen_name numQueries
*/ 

	//Include Abraham's TwitterOAuth Library
	require_once("twitteroauth/twitteroauth/twitteroauth.php");

	//Retrieve the keys and secrets from the command line
	$CONSUMER_KEY = $argv[1];
	$CONSUMER_SECRET = $argv[2];
	$OAUTH_TOKEN = $argv[3];
	$OAUTH_TOKEN_SECRET = $argv[4];
	
	//Retrieve remaining arguments for clarity
	$screenName = $argv[5];
	$numQueries = $argv[6];

	$connection = new TwitterOAuth($CONSUMER_KEY, $CONSUMER_SECRET, $OAUTH_TOKEN, $OAUTH_TOKEN_SECRET);
	$userInfo = $connection->get('users/show', array('screen_name' => $screenName));

	print_r($userInfo);
?>
