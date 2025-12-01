import { Link, useNavigate } from "react-router-dom";
import type { AuthStatus } from "../../generated/api-client";
import { Dropdown, type MenuProps } from "antd";
import { logout } from "../../services/AuthService.ts";
import { getRoute, Routes } from "../../util/routes.ts";
import logo from "../../assets/logo.svg";
import { PaymentIcon } from "../icons/PaymentIcon.tsx";
import { ChartIcon } from "../icons/ChartIcon.tsx";
import { InboxIcon } from "../icons/InboxIcon.tsx";
import { LogoutIcon } from "../icons/LogoutIcon.tsx";
import { SettingsIcon } from "../icons/SettingsIcon.tsx";

interface AppAsideProps {
  auth: AuthStatus;
}

export default function AppAside({ auth }: AppAsideProps) {
  const navigate = useNavigate();

  function onDropdownClick(info: { key: string }) {
    switch (info.key) {
      case "logout":
        logout();
        break;
    }
  }

  function onLogoClick() {
    if (auth.loggedIn) {
      navigate(Routes.Dashboard);
    } else {
      window.location.href = "/";
    }
  }

  const items: MenuProps["items"] = [
    {
      label: (
        <Link to={Routes.Settings}>
          <SettingsIcon />
          Settings
        </Link>
      ),
      key: "settings",
    },
    {
      label: (
        <a>
          <LogoutIcon />
          Logout
        </a>
      ),
      key: "logout",
    },
  ];

  return (
    <aside>
      <header>
        <a className="logo" onClick={onLogoClick}>
          <img src={logo.src} alt="logo" />
        </a>
      </header>

      {auth.isAuthor && (
        <div className="menu">
          <h5>Admin Dashboard</h5>
          <ul>
            <li>
              <Link to={Routes.Products}>
                <InboxIcon />
                Products
              </Link>
            </li>
            <li>
              <Link to={Routes.Payments}>
                <PaymentIcon />
                Payments
              </Link>
            </li>
            <li>
              <Link to={Routes.Statistics}>
                <ChartIcon />
                Analytics
              </Link>
            </li>
          </ul>
        </div>
      )}

      <footer>
        <Dropdown
          menu={{ items, onClick: onDropdownClick }}
          trigger={["click"]}
        >
          <button className="act-btn">
            <div className="avatar">
              <a onClick={(e) => e.preventDefault()}>{auth.label}</a>
            </div>
            <h6>{auth.name || "Your Account"}</h6>
          </button>
        </Dropdown>
      </footer>
    </aside>
  );
}
