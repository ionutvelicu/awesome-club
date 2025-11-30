import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import MyCourses from "./MyCourses";
import Products from "./Products";
import Payments from "./Payments";
import Statistics from "./Statistics";
import ProductDetails from "./ProductDetails";
import CourseView from "./CourseView";
import type { AuthStatus } from "../../generated/api-client";
import { useEffect, useState } from "react";
import AuthApi from "../../api/AuthApi.ts";
import { setAuthStore } from "../../store/auth.ts";
import { Skeleton } from "antd";
import { handleAxiosError } from "../../services/ErrorService.ts";
import AppAside from "./AppAside.tsx";
import Settings from "./Settings.tsx";

export default function App() {
  const [loading, setLoading] = useState(true);
  const [auth, setAuth] = useState<AuthStatus>({} as AuthStatus);

  useEffect(() => {
    AuthApi.getStatus()
      .then((resp) => {
        if (!resp.data.loggedIn) {
          window.location.href = "/";
          return;
        }
        setAuth(resp.data);
        setAuthStore(resp.data);
        setLoading(false);
      })
      .catch((err) => {
        handleAxiosError(err);
        window.location.href = "/";
      });
  }, []);

  return loading ? (
    <div className="app-loading">
      <Skeleton active loading />
      <h3>Loading...</h3>
    </div>
  ) : (
    <div className="app">
      <BrowserRouter basename="/app">
        <AppAside auth={auth}/>

        <main>
          <Routes>
            <Route index element={<Products />} />

            <Route path="products" element={<Products />} />
            <Route path="products/new" element={<ProductDetails />} />
            <Route path="products/:productId" element={<ProductDetails />} />
            <Route path="payments" element={<Payments />} />
            <Route path="statistics" element={<Statistics />} />
            <Route path="settings" element={<Settings />} />

            <Route path="courses/:purchaseId" element={<CourseView />} />
            <Route path="courses" element={<MyCourses />} />
          </Routes>
        </main>
      </BrowserRouter>
    </div>
  );
}
