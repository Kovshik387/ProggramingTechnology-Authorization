import HeaderNavigation from "@components/Header"

export default function AuthorizationPage(){
    return <>
        <HeaderNavigation/>
        <h1 style={CenterText}>Добро пожаловать</h1>
    </>
}

const CenterText : React.CSSProperties = {
    textAlign: 'center'
}