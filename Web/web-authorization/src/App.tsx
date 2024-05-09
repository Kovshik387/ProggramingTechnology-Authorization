import * as ReactRouter from "react-router-dom";
import AuthorizationPage from '@pages/AuthorizationPage';
import SignInPage from '@pages/Login/SignInPage';
import SignUpPage from "@pages/Login/SignUpPage";
import RememberPasswordPage from "@pages/RestorePasssword/RememberPasswordPage";
import RestorePasswordPage from "@pages/RestorePasssword/RestorePasswordPage"
import HeaderNavigation from "@components/Header"
import Footer from "@components/Footer";
import ConfirmPage from "@pages/Login/ConfirmPage";


export default function App(){
	
	const router = ReactRouter.createBrowserRouter([
		{
			path: '/',
			element: <AuthorizationPage/>
		},
		{
			path: '/signIn',
			element: <SignInPage/>
		},
		{
			path: '/signUp',
			element: <SignUpPage/>
		},
		{
			path: '/forgotPassword',
			element: <RememberPasswordPage/>
		},
		{
			path: '/p/:code',
			element: <RestorePasswordPage/>,
		},
		{
			path: '/c/:code',
			element: <ConfirmPage/>
		}

	]);
	return (
		<div style={mainContent}>
			<HeaderNavigation/>
			<ReactRouter.RouterProvider router={router} />
			<Footer/>
		</div>
	);
	

}
const mainContent : React.CSSProperties = {
	display: "flex",
	justifyContent: "flex-end",
	flexDirection: "column",
	width: "100%",
	height: "100%",
	flex: 1
}