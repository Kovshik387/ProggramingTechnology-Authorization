import { CFooter, CLink } from '@coreui/react'
import { Footer } from "flowbite-react";


export default function FooterComponent() {
  return (
    <footer style={FooterStyle} className='justify-content-end'>
        <h1 style={{color: 'white'}}>        
        <div style={{color: 'white'}} >
            <a style={{textDecoration: "none"}} href="https://github.com/Kovshik387/ProggramingTechnology-Authorization">GitHub</a>
        </div></h1>
    </footer>
  );
}

const FooterStyle : React.CSSProperties = {
    backgroundColor: "#242424",
    color: "white",
    display: "flex",
    alignItems: "center",
}