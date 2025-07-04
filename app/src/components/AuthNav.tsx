import { useEffect, useState } from "react";
import AuthApi from "../api/AuthApi";
import type { AuthStatus } from "../generated/api-client";
import { Button, Dropdown, type MenuProps } from "antd";
import Storage from "../util/storage";
import { Routes } from "../util/routes";
import { setAuthStore } from "../store/auth";

export default function AuthNav() {
  const [auth, setAuth] = useState<AuthStatus | undefined>();

  useEffect(() => {
    AuthApi.getStatus().then((resp) => {
      setAuth(resp.data);
      setAuthStore(resp.data);
    });
  }, []);

  function logout() {
    Storage.removeToken();
    window.location.href = Routes.Root;
  }

  function onDropdownClick(info: { key: string }) {
    switch (info.key) {
      case "logout":
        logout();
        break;
    }
  }

  const items: MenuProps["items"] = [
    {
      label: <a href={Routes.Dashboard}>Dashboard</a>,
      key: "dashboard",
    },
    { label: <a href={Routes.Courses}>My Courses</a>, key: "courses" },
    { label: <a>Logout</a>, key: "logout" },
  ];

  return (
    <nav>
      {auth && auth.loggedIn && (
        <>
          <Dropdown
            className="avatar"
            menu={{ items, onClick: onDropdownClick }}
            trigger={["click"]}
          >
            <a onClick={(e) => e.preventDefault()}>{auth.label}</a>
          </Dropdown>
        </>
      )}

      {auth && !auth.loggedIn && (
        <>
          <Button type="link" href="/start">
            Register
          </Button>
          <Button href="/login">Log In</Button>
        </>
      )}
    </nav>
  );
}
