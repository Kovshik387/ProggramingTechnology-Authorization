import {useNavigate } from 'react-router-dom'
import { Container,Form, InputGroup} from "react-bootstrap"
import { Button} from '@mui/material'
import { Eye,EyeSlashFill } from "react-bootstrap-icons"
import React from 'react';

export type AuthRequest = {
    firstName: string,
    lastName: string,
    surnName: string | null,
    
    email: string,
    password: string,
    role: number
};

export default function SignInPage(){

    const navigate = useNavigate();
    if(localStorage.getItem('id') != null) navigate('/');
    
    const [showPassword, setShowPassword] = React.useState(false);
    const [errorEmail, setErrorEmail] = React.useState("");
    const [errorPassword, setErrorPassword] = React.useState("");
    const [email,setEmail] = React.useState("");
    const [password,setPassword] = React.useState("");

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        e.stopPropagation();
        
        const account: AuthRequest =  {email: email,password: password};
        const data = await signIn(account);

        if(data.errorMessage != null){
            if(data.errorMessage.match("пароль")){
                setErrorPassword(data.errorMessage);
            }
            else setErrorEmail(data.errorMessage);
        }else{
            localStorage.setItem("id",data.id!.toString());
            localStorage.setItem("accessToken",data.accessToken!);
            localStorage.setItem("refreshToken",data.refreshToken!);

            navigate('/');
            window.location.reload()
        }
    }

    return <>
 <div style={{flex: 1}}>
        <Container fluid="md">
            <div style={{alignItems: 'center',display: 'flex',justifyContent: 'center',minHeight: '500px'}}>
                <div style = {{border: "1px solid black", borderRadius: "5px",padding: "20px"}}>
                    <p>Авторизация</p>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="email">
                            <Form.Label>Почта</Form.Label>
                            <InputGroup hasValidation>
                                <Form.Control isInvalid={errorEmail != ""} type="email" name="email" value={email} onChange={(e => setEmail(e.target.value))} placeholder="name@example.com" required />
                                <Form.Control.Feedback type="invalid">
                                    {errorEmail}
                                </Form.Control.Feedback>
                            </InputGroup>
                        </Form.Group>
                        <Form.Group className="mb-3" id="userPassword">
                            <Form.Label>Пароль</Form.Label>
                            <InputGroup hasValidation>
                                <Form.Control isInvalid = {errorPassword != ""} type={showPassword ? "text" : "password"} value={password} onChange={(e => setPassword(e.target.value))} name="password" id="passwordVisible" placeholder="Password" />
                                <Button style={{backgroundColor: "#242424"}} variant="contained" onClick={() => setShowPassword(!showPassword)}>
                                    {showPassword ? <EyeSlashFill color="white" size={20}/> : <Eye color="white" size={20}></Eye>}
                                </Button>
                                <Form.Control.Feedback type="invalid">
                                    {errorPassword}
                                </Form.Control.Feedback>
                            </InputGroup>
                            <Form.Label>
                                <a className="justify-content-end" href="/ForgotPassword">Забыли пароль?</a>
                            </Form.Label> 
                        </Form.Group>

                        <Form.Group className="mb-3" >
                            <Form.Label>Фамилия</Form.Label>
                            <InputGroup hasValidation>
                                <Form.Control name="password" id="passwordVisible" placeholder="Password" />
                                <Button style={{backgroundColor: "#242424"}} variant="contained" onClick={() => setShowPassword(!showPassword)}>
                                    {showPassword ? <EyeSlashFill color="white" size={20}/> : <Eye color="white" size={20}></Eye>}
                                </Button>
                                <Form.Control.Feedback type="invalid">
                                    {errorPassword}
                                </Form.Control.Feedback>
                            </InputGroup>
                            <Form.Label>
                                <a className="justify-content-end" href="/ForgotPassword">Забыли пароль?</a>
                            </Form.Label> 
                        </Form.Group>
                        <Button variant="contained" type="submit" >Войти</Button>
                    </Form>
                </div>
            </div>
        </Container>    
    </div>
    </>
}