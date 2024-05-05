import { Navbar,Container,Nav} from "react-bootstrap";

export default function HeaderNavigation(){
  return (
    <>
        <Navbar bg="dark" data-bs-theme="dark" className="bg-body-tertiary">
        <Container >
          <Navbar.Brand href="/">Сайт</Navbar.Brand>
          <Nav>
            <Navbar.Toggle></Navbar.Toggle>
            {
              
              localStorage.getItem("id") == null ?
              <Navbar.Collapse hidden={localStorage.getItem("id") === undefined } className="justify-content-end">
                  <Nav.Link href="/signIn">Войти</Nav.Link>
                  <Nav.Link href="/signUp">Регистрация</Nav.Link>
              </Navbar.Collapse>

              :

              <Navbar.Collapse className="justify-content-end">  
                <Navbar.Text>
                    Привет! {localStorage.getItem("id")}
                </Navbar.Text>
                <Nav.Link href="/logout">Выйти</Nav.Link>
              </Navbar.Collapse>
            }
          </Nav>
        </Container>
      </Navbar>
    </>
    )
}
