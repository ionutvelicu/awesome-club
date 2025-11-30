export const AppRoute = "/app";

export const Routes = {
  Dashboard: "/",
  Products: "/products",
  Payments: "/payments",
  Statistics: "/statistics",
  Settings: "/settings",
  ProductsNew: "/products/new",
};

export function getRoute(route: string) {
  return `${AppRoute}${route}`;
}
