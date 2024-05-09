import {useNavigate } from 'react-router-dom'
import { Col, Container,Form,InputGroup, Row} from "react-bootstrap"
import { Button} from '@mui/material'
import { Eye,EyeSlashFill } from "react-bootstrap-icons"
import React from 'react';
export type AuthRequest = {
    firstName: String,
    lastName: String,
    surnName: String | null,
    
    email: String,
    password: String,
    role: Number
};

export type AuthResponse = {
    id: string | null,
    role: number,
    accessToken: string | null,
    refreshToken: string | null,
    errorMessage: Array<String> | null
};

export default function SignInPage(){

    const navigate = useNavigate();
    if(localStorage.getItem('id') != null) navigate('/');
    
    const [showPassword, setShowPassword] = React.useState(false);
    const [errorValue, setError] = React.useState("");
    const [password,setPassword] = React.useState("");

    const signUpData = async(userData: AuthRequest): Promise<AuthResponse> => {
        const headers = new Headers();
        headers.set('Access-Control-Allow-Origin', '*');
        headers.set('Content-Type','application/json');
        const url = `http://localhost:8080/api/SignUp`
        const response = await fetch(url,{method: 'Post',headers: headers,body: JSON.stringify({
            "firstName": userData.firstName,
            "surname": userData.surnName,
            "lastname": userData.lastName,
            "email": userData.email,
            "password": userData.password,
            "role": userData.role 
        })});
        return await response.json();
    }

    const handleSubmit = async (event:  React.SyntheticEvent<HTMLFormElement>) => {
        event.preventDefault();
        event.stopPropagation();
        
        const form = event.currentTarget;
        const formElements = form.elements as typeof form.elements & {
            email: {value: String},
            password: {value: String},
            role: {value: Number},
            name: {value: String},
            lastname: {value: String},
            surname: {value: String | null}
        }

        const account: AuthRequest =  {
            email: formElements.email.value,
            password: formElements.password.value,
            role: formElements.role.value,
            firstName: formElements.name.value,
            lastName: formElements.lastname.value,
            surnName: formElements.surname.value
        };

        var response = await signUpData(account); console.log(response);
        if (response.errorMessage != null){
            setError(response.errorMessage![0].toString());
            window.alert(response.errorMessage![0].toString());
        }
        else{
            //localStorage.setItem("id",response.id!.toString());
            window.alert("На почту выслано письмо для подтверждения почты");
            navigate('/signIn');
        }
    }

    return <>
 <div style={{flex: 1}}>
        <Container fluid="md">
            <div style={{alignItems: 'center',display: 'flex',justifyContent: 'center',minHeight: '500px'}}>
                <div style = {{border: "1px solid black", borderRadius: "5px",padding: "20px"}}>
                    <h3>Регистрация</h3>
                    <Form onSubmit={handleSubmit} >
                        <Row>
                            <Col>
                                <Form.Group className="mb-3" >
                                    <Form.Label>Почта</Form.Label>
                                    <InputGroup hasValidation >
                                        <Form.Control type="email" name="email" id="email" placeholder="name@example.com" required />
                                    </InputGroup>

                                    <Form.Label>Пароль</Form.Label>
                                    <InputGroup hasValidation >
                                        <Form.Control  type={showPassword ? "text" : "password"} value={password} onChange={(e => setPassword(e.target.value))} name="password" id="passwordVisible" placeholder="Password" />
                                        <Button style={{backgroundColor: "#242424"}} variant="contained" onClick={() => setShowPassword(!showPassword)}>
                                            {showPassword ? <EyeSlashFill color="white" size={20}/> : <Eye color="white" size={20}></Eye>}
                                        </Button>

                                    </InputGroup>

                                    <Form.Label>Роль</Form.Label>
                                    <InputGroup >
                                        <Form.Select name="role" id="role" aria-label='Роль' >
                                            <option value="1">Участник</option>
                                            <option value="2">Организатор</option>
                                        </Form.Select>
                                    </InputGroup>

                                </Form.Group>
                            </Col>
                            
                            <Col>
                                <Form.Group className="mb-3" >
                                    <Form.Label>Фамилия</Form.Label>
                                    <InputGroup hasValidation  >
                                        <Form.Control id="lastname" name="lastname"/>
                                    </InputGroup>

                                    <Form.Label>Имя</Form.Label>
                                    <InputGroup hasValidation>
                                        <Form.Control id="name" name="name" />
                                    </InputGroup>

                                    <Form.Label>Отчество</Form.Label>
                                    <InputGroup hasValidation >
                                        <Form.Control id="surname" name="surname"/>
                                    </InputGroup>
                                </Form.Group>

                            </Col>

                                

                                


                            <Button  variant="contained" type="submit" id="submBtn" >Зарегестрироваться</Button>

                            
                        </Row>
                    </Form>
                </div>
            </div>
        </Container>    
    </div>
    </>
}