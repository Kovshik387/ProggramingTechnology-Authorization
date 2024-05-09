import * as ReactRouter from "react-router-dom";
import { Navigate } from "react-router-dom";

export default function ConfirmPage(){
    const params = ReactRouter.useParams();
    const code = params.code;
    console.log(code);
    
    const confirmRequest = async(code: String): Promise<String> => {
        const headers = new Headers();
        headers.set('Access-Control-Allow-Origin', '*');
        const url = `http://localhost:8080/api/ConfirmAccount?code=${code}`
        const response = await fetch(url,{method: 'Post',headers: headers});
        return await response.text();
    }
    
    if (code === undefined){
        return <Navigate to="/"/>
    }

    var data = confirmRequest(code);
    console.log("data: " + data);
    return <>
        <Navigate to="/signIn"/>
    </>
}