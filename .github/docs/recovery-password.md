## Send recovery code
```http
POST /recovery-code/email
```

#### Request Body
```javascript
{
  "email": "user@gmail.com",
}
```

<hr>

## Validate recovery code
```http
GET /recovery-password/pin?code=3610&email=user001@gmail.com
```

#### Request Params

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `code` | `string` | **Required**.|
| `email` | `string` | **Required**.|

<hr>

## Update password by recovery code
```http
PATCH /recovery-password/password
```

#### Request Body
```javascript
{
  "email": "user001@gmail.com",
  "password": "develop",
  "code": "3610"
}
```