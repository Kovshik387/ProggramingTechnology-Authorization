import HeaderNavigation from "@components/Header"

export default function Logout(){
    localStorage.removeItem("id");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    return <>
        <HeaderNavigation/>
    </>
}