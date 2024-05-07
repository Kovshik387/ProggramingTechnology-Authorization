import HeaderNavigation from "@components/Header"

export default function AuthorizationPage(){
    return <>
    <div style={{flex: 1}}>
        <h1 style={CenterText}>Добро пожаловать</h1>
    </div>
    </>
}

const CenterText : React.CSSProperties = {
    textAlign: 'center'
}