import React from 'react'
import * as ReactRouter from "react-router-dom";

export interface NavigatorProps{
  navigator: ReactRouter.NavigateFunction;
  location: ReactRouter.Location
}

type NavigationElementProps = { 
	element: typeof React.Component<NavigatorProps, {}>,
}
export function NavigationElement(props: NavigationElementProps) : React.JSX.Element {
	const navigator = ReactRouter.useNavigate();
	const location = ReactRouter.useLocation();
    return (React.createElement(props.element, { 'navigator': navigator, 'location': location }));
}

export default function App(_props: {}): React.JSX.Element {
	const router = ReactRouter.createBrowserRouter([
		{
			path: '/',
			element: <NavigationElement element={NotificationsPage}/>
		},
		{
			path: '/notification',
			element: <NavigationElement element={DetailsInfoPage}/>
		},
		{
			path: '/owner',
			element: <NavigationElement element={OwnerPage}/>
		},
		{
			path: '/owner/meeting',
			element: <NavigationElement element={MeetingInfoPage}/>
		}
	]);
	return (
		<ReactRouter.RouterProvider router={router} />
	);
}
