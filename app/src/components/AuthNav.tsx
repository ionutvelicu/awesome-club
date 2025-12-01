import { useEffect, useState } from "react";
import AuthApi from "../api/AuthApi";
import type { AuthStatus } from "../generated/api-client";
import { Button } from "antd";
import { getRoute, Routes } from "../util/routes";
import { setAuthStore } from "../store/auth";
import { logout } from "../services/AuthService.ts";

export default function AuthNav() {
  const [auth, setAuth] = useState<AuthStatus | undefined>();

  useEffect(() => {
    AuthApi.getStatus().then((resp) => {
      setAuth(resp.data);
      setAuthStore(resp.data);
    });
  }, []);

  return (
    <nav className="auth-nav">
      {auth && auth.loggedIn && (
        <>
          <a className={"lk"} href={getRoute(Routes.Dashboard)}>Dashboard</a>
          <a className={"lk"} onClick={logout}>Logout</a>
        </>
      )}

      {auth && !auth.loggedIn && (
        <>
          <Button size={"large"} type="link" href="/start">
            Register
          </Button>
          <Button size={"large"} href="/login">
            Log In
          </Button>
        </>
      )}
    </nav>
  );
}
