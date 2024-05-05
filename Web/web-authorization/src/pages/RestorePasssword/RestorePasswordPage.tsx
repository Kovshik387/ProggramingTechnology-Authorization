import HeaderNavigation from "@components/Header"
import { Form, InputGroup} from "react-bootstrap"
import { Button} from '@mui/material'
import React from "react";
import * as ReactRouter from "react-router-dom";
import { Eye,EyeSlashFill } from "react-bootstrap-icons"
import {useNavigate } from 'react-router-dom'

export default function RestorePasswordPage(){
    const params = ReactRouter.useParams();
    const id = params.code;
    
    const [password,setPassword] = React.useState("");
    const [replyPassword,setReplyPassword] = React.useState("");
    const [showPassword, setShowPassword] = React.useState(false);
    const [errorPassword,setErrorPassword] = React.useState("");

    const navigate = useNavigate();

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        e.stopPropagation();

        const data = await setNewPassword(id!,password,replyPassword);
        if(!data.match("Успешно")){
            setErrorPassword(data.toString());
        }
        else{
            navigate('/signIn');
        }
    }
    
    async function setNewPassword(id:string ,password: String,confirmPassword: String): Promise<String> {
        const headers = new Headers();
        headers.set('Access-Control-Allow-Origin', '*');
        const url = `http://localhost:8080/api/NewPassword?code=${id}&password=${password}&confirmPassword=${confirmPassword}`
        const response = await fetch(url,{method: 'POST',headers: headers});
        if (!response.ok){
            alert("Произошла ошибка");
        }
        return await response.text();
    }

    return <>
        <HeaderNavigation/>
        <div style={{alignItems: 'center',display: 'flex',justifyContent: 'center',minHeight: '500px'}}>
            <div style = {{border: "1px solid black", borderRadius: "5px",padding: "20px"}}>
            <h5>Восстановление пароля</h5>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label>Введите пароль</Form.Label>
                        <InputGroup hasValidation>
                            <Form.Control  type={showPassword ? "text" : "password"} name="password" value={password} onChange={(e => setPassword(e.target.value))} required />
                        </InputGroup>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <InputGroup hasValidation>
                            <Form.Control isInvalid={errorPassword != ""}  type={showPassword ? "text" : "password"} name="password" value={replyPassword} onChange={(e => setReplyPassword(e.target.value))} required />
                            <Form.Control.Feedback type="invalid">
                                {errorPassword}
                            </Form.Control.Feedback>
                        </InputGroup>
                    </Form.Group>
                    <Form.Group>
                        <InputGroup className="mb-3 px-2">
                            <Button variant="contained" type="submit" >Восстановить</Button>
                            <Button style={{backgroundColor: "#242424"}} variant="contained" onClick={() => setShowPassword(!showPassword)}>
                                            {showPassword ? <EyeSlashFill color="white" size={25}/> : <Eye color="white" size={25}></Eye>}
                            </Button>
                        </InputGroup>
                    </Form.Group>
                </Form>
            </div>
        </div>
    </>
}
