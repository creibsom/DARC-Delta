<?php

/**
* Author: Cody Reibsome (creibsom@mail.umw.edu)
* Last Updated: 1/19/2014
* Description: Uses Abraham's TwitterOAuth Library to:
*	1) Query the given screen_name's id (idOrRelations == 0)
*	2) Query all of the given user_id's friends and followers (idOrRelations != 0)
* Usage: ./findFollowerRelations.php CONSUMER_KEY CONSUMER_SECRET OAUTH_TOKEN OAUTH_TOKEN_SECRET screenNameOrId idOrRelations
*/ 

	//Include Abraham's TwitterOAuth Library
	require_once("twitteroauth/twitteroauth/twitteroauth.php");

	//Retrieve the keys and secrets from the command line
	$CONSUMER_KEY = $argv[1];
	$CONSUMER_SECRET = $argv[2];
	$OAUTH_TOKEN = $argv[3];
	$OAUTH_TOKEN_SECRET = $argv[4];
	
	//Open a TwitterOAuth 'connection' and query the screen_name given in argv[5]
	$connection = new TwitterOAuth($CONSUMER_KEY, $CONSUMER_SECRET, $OAUTH_TOKEN, $OAUTH_TOKEN_SECRET);

	//If we're just retrieving the user_id for the provided screen_name:
	if($argv[6] == 0) {
		$screenName = $argv[5];
		$userInfo = $connection->get('users/show', array('screen_name' => $screenName));

		//Pull and print the id from the JSON-decoded userInfo
		print_r($userInfo->{'id'});
	}
	//Otherwise, we're provided a user_id and expected to find the friends and followers:
	else {
		$id = $argv[5];
		$curs = -1;
		//Twitter API pulls 5000 id's at a time, if there are more, $curs != 0
		do {
			//Query data
			$followerInfo = $connection->get('followers/ids', array('user_id' => $id, 'cursor' => $curs));
			$followerList = $followerInfo->{'ids'};
		
			//Dump in files
			foreach($followerList as $followerId) {
				file_put_contents("data.csv", $followerId.",".$id."\n", FILE_APPEND | LOCK_EX);
				file_put_contents("id_dump.txt", $followerId."\n", FILE_APPEND | LOCK_EX);
			}
			//Check if more data to query
			$curs = $followerInfo->{'next_cursor'};

			//Print out how many queries remain until the rate limit
			$rateLimit = $connection->get('application/rate_limit_status', array('resources' => 'followers'));
			$followersL = $rateLimit->{'resources'};
			$followerIdsL = $followersL->{'followers'};
			print("Follower Query Limit:\n");
			print_r($followerIdsL->{'/followers/ids'}); 
		}while($curs != 0);
	
		//Repeat for friends
		$curs = -1;
		do {
			$friendInfo = $connection->get('friends/ids', array('user_id' => $id));
			$friendList = $friendInfo->{'ids'};

			foreach($friendList as $friendId) {
				file_put_contents("data.csv", $id.",".$friendId."\n", FILE_APPEND | LOCK_EX);
				file_put_contents("id_dump.txt", $friendId."\n", FILE_APPEND | LOCK_EX);
			}
			$curs = $friendInfo->{'next_cursor'};
			
			//Print out how many queries remain until the rate limit
			$rateLimit = $connection->get('application/rate_limit_status', array('resources' => 'friends'));
			$friendsL = $rateLimit->{'resources'};
			$friendIdsL = $friendsL->{'friends'};
			print("Friend Query Limit:\n");
			print_r($friendIdsL->{'/friends/ids'}); 
		}while($curs != 0);
	}
?>
