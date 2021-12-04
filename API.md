# **Finish IT** API list

## *User*

**POST** /auth
Authorize DONE
```
{
    username: String
    password: String
}
```

**POST** /register
Register DONE
```
{
    username: String,
    password: String
}
```

**GET** /user DONE

**PUT** /user DONE

**DELETE** /user DONE

## *Document*

**POST** /doc DONE
```
{
    title: String,
    description: String
}
```

**GET** /doc DONE

**GET** /doc/{id} DONE

**PUT** /doc/{id} DONE
```
{
    title: String,
    description
}
```

**DELETE** /doc/{id} DONE

**POST** /doc/{id}/users DONE
```
{
    username: String
}
```

**DELETE** /doc/{id}/users/{username} DONE

### *Task*

**POST** /doc/{id}/task DONE
```
{
    content: String
    deadline?: Date
}
```

**PUT** /doc/{id}/task/{id} DONE
```
{
    content: String
    deadline?: Date
}
```

**DELETE** /doc/{id}/task{id} DONE

**PUT** /doc/{id}/task/{id}/do DONE

**PUT** /doc/{id}/task/{id}/undo DONE

### *Comment*

**POST** /doc/{id}/comment DONE
```
{
    content: String
}
```

**PUT** /doc/{id}/comment/{id} DONE
```
{
    content: String
}
```

**DELETE** /doc/{id}/comment/{id} DONE

## DocContentType:
*TASK, COMMENT*
