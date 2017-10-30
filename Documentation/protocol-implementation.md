# Protocol implementation


## Client

### login_request
```json
{
  "type": "login_request",
  "userId": "<user_id>",
  "password": "<user_password>"
}
```

### game_start_request
```json
{
  "type": "game_start_request",
  "userId": "<user_id>",
  "timestamp": "<date_time>",
  "game_type": "standard|tournament_comp|tournament_friend"
}
```

### play_move
```json
{
  "type": "play_move",
  "userId": "<user_id>",
  "gameId": "<game_id>",
  "timestamp": "<date_time>",
  "word": "<word_played>"
}
```

### use_power
```json
{
  "type": "play_move",
  "userId": "<user_id>",
  "gameId": "<game_id>",
  "timestamp": "<date_time>",
  "power": "discard_rack|discard_letter|peek|hint|word_analyzer"
}
```

### chat_message
```json
{
  "type": "chat_message",
  "userId": "<user_id>",
  "gameId": "<game_id>",
  "timestamp": "<date_time>",
  "message": "<text_content>"
}
```


## Server

### login_response
```json
{
  "type": "login_response",
  "valid": "<connection_accepted>"
}
```

### game_start_response
```json
{
  "type": "game_start_response",
  "gameId": "<game_id>",
  "timestamp": "<date_time>",
  "status": "waiting|started|???"
}
```

### new_game_started
```json
{
  "type": "new_game_started",
  "gameId": "<game_id>",
  "game_type": "standard|tournament_comp|tournament_friend",
  "timestamp": "<date_time>",
  "opponent": "<user_name>"
}
```

### game_move_update
```json
{
  "type": "game_move_update",
  "userId": "<user_id>",
  "gameId": "<game_id>",
  "timestamp": "<date_time>",
  "word": "<word_played>"
}
```

### game_power_update
```json
{
  "type": "game_power_update",
  "userId": "<user_id>",
  "gameId": "<game_id>",
  "timestamp": "<date_time>",
  "power": "discard_rack|discard_letter",
  "lettersChamged": "<array_letters>"
}
```
