import { Outlet, Link } from "react-router-dom";

export default function Dashboard() {
  return (
    <div>
      <nav>
        <Link to="products">Products</Link>
        <Link to="payments">Payments</Link>
        <Link to="statistics">Statistics</Link>
      </nav>
      <div>
        <Outlet />
      </div>
    </div>
  );
}
