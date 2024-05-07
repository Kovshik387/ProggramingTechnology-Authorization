import { Navbar,Container,Nav} from "react-bootstrap";

export type HeaderProps = {
  state: [string, React.Dispatch<React.SetStateAction<string>>]
}

export default function HeaderNavigation(){
  return (
    <>
        <Navbar bg="dark" data-bs-theme="dark" className="bg-body-tertiary">
        <Container >
          <Navbar.Brand href="/">Сайт</Navbar.Brand>
          <Nav>
            <Navbar.Toggle></Navbar.Toggle>
            {
              localStorage.getItem("id") === null ?

              <Navbar.Collapse className="justify-content-end">
                  <Nav.Link href="/signIn">Войти</Nav.Link>
                  <Nav.Link href="/signUp">Регистрация</Nav.Link>
              </Navbar.Collapse>

              :

              <Navbar.Collapse className="justify-content-end">  
                <Navbar.Text>
                    Привет! {localStorage.getItem("id")}
                </Navbar.Text>
                <Nav.Link onClick={() => {
                  localStorage.removeItem("id");
                  window.location.reload();
                }} >Выйти</Nav.Link>
              </Navbar.Collapse>
            }
          </Nav>
        </Container>
      </Navbar>
    </>
    )
}
