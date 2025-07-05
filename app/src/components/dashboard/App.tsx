import { BrowserRouter, Routes, Route } from "react-router-dom";
import MyCourses from "./MyCourses";
import Dashboard from "./Dashboard";
import Products from "./Products";
import Payments from "./Payments";
import Statistics from "./Statistics";
import ProductDetails from "./ProductDetails";
import CourseView from "./CourseView";

export default function App() {
  return (
    <BrowserRouter basename="/app">
      <Routes>
        <Route path="/courses/:purchaseId" element={<CourseView />} />
        <Route path="/courses" element={<MyCourses />} />
        <Route path="/dashboard" element={<Dashboard />}>
          <Route index element={<Products />} />
          <Route path="products" element={<Products />} />
          <Route path="products/new" element={<ProductDetails />} />
          <Route path="products/:productId" element={<ProductDetails />} />
          <Route path="payments" element={<Payments />} />
          <Route path="statistics" element={<Statistics />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
