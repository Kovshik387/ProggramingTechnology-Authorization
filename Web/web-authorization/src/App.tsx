import * as ReactRouter from "react-router-dom";
import AuthorizationPage from '@pages/AuthorizationPage';
import SignInPage from '@pages/Login/SignInPage';
import SignUpPage from "@pages/Login/SignUpPage";
import LogoutPage from "@pages/Login/LogoutPage";
import RememberPasswordPage from "@pages/RestorePasssword/RememberPasswordPage";
import RestorePasswordPage from "@pages/RestorePasssword/RestorePasswordPage"

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
			path: '/logout',
			element: <LogoutPage/>
		},
		{
			path: '/forgotPassword',
			element: <RememberPasswordPage/>
		},
		{
			path: '/p/:code',
			element: <RestorePasswordPage/>,
		}
	]);
	return (
		<ReactRouter.RouterProvider router={router} />
	);
}
