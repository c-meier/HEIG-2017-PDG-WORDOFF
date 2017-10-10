# Protocol specification


## Client

### login_request
```
{
  "type": "login_request",
  "user": "<user_name>",
  "password": "<user_password>"
}
```

### game_start_request
```
{
  "type": "game_start_request"
  "user": "<user_name>",
  "game_type": "standard|tournament_comp|tournament_friend"
}
```

### play_move
```
{
  "type": "play_move",
  "user": "<user_name>",
  "gameID": "<game_id>",
  "word": "<word_played>"
}
```

### chat_message
```
{
  "type": "chat_message",
  "user": "<user_name>",
  "gameID": "<game_id>",
  "message": "<text_content>"
}
```

### use_power
```
{
  "type": "play_move",
  "user": "<user_name>",
  "gameID": "<game_id>",
  "power": "discard_rack|discard_letter|peek|hint|word_analyzer",
}
```


## Server

### login_response
```
{

}
```

### game_start_response
```
{

}
```

### new_game_started
```
{

}
```

### game_state_update
```
{

}
```
