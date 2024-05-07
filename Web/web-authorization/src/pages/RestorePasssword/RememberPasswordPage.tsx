import { Form, InputGroup} from "react-bootstrap"
import { Button} from '@mui/material'
import React from "react";


export default function RememberPasswordPage(){
    
    async function forgotPassword(email: String): Promise<String> {
        const headers = new Headers();
        headers.set('Access-Control-Allow-Origin', '*');
        const url = `http://localhost:8080/api/ForgetPassword?email=${email}`
        const response = await fetch(url,{method: 'GET',headers: headers});
        if (!response.ok){
            alert("Произошла ошибка")
        }
        return await response.text();
    }

    const [errorEmail, setErrorEmail] = React.useState("");
    const [validEmail, setValidEmail] = React.useState(false);
    const [email,setEmail] = React.useState("");
    const handleSubmit = async (e: any) => {
        e.preventDefault();
        e.stopPropagation();

        const data = await forgotPassword(email);
        if(data.match("Пользователя")){
            setErrorEmail(data.toString())
        }
        else{
            setValidEmail(true);
            setErrorEmail("");
        }
    }

    return <>
    <div style={{flex: 1}}>
        <div style={{alignItems: 'center',display: 'flex',justifyContent: 'center',minHeight: '500px'}}>
            <div style = {{border: "1px solid black", borderRadius: "5px",padding: "20px"}}>
            <h5>Восстановление пароля</h5>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="email">
                        <Form.Label>Почта</Form.Label>
                        <InputGroup hasValidation>
                            <Form.Control isInvalid={errorEmail != ""} isValid={validEmail} type="email" name="email" value={email} onChange={(e => setEmail(e.target.value))} placeholder="name@example.com" required />
                            <Form.Control.Feedback type="invalid">
                                {errorEmail}
                            </Form.Control.Feedback>
                            <Form.Control.Feedback type="valid">
                                Письмо отправлено
                            </Form.Control.Feedback>
                        </InputGroup>
                    </Form.Group>
                    <Button variant="contained" type="submit" >Восстановить</Button>
                </Form>
            </div>
        </div>
    </div>
    </>
}