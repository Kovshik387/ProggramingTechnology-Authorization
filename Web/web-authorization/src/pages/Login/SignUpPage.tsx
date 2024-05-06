import HeaderNavigation from "@components/Header"
import {useNavigate } from 'react-router-dom'

export default function SignInPage(){
    const navigate = useNavigate();
    if(localStorage.getItem('id') != null) navigate('/');
    
    return <>
        <HeaderNavigation/>
    </>
}