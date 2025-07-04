import { Button, Input, message } from "antd";
import { useState } from "react";
import AuthApi from "../../api/AuthApi";
import Storage from "../../util/storage";
import { handleAxiosError } from "../../services/ErrorService";
import { Routes } from "../../util/routes";

export default function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  async function login() {
    setLoading(true);
    AuthApi.login(email, password)
      .then((resp) => {
        Storage.setToken(resp.data.token);
        window.location.href = Routes.Dashboard;
      })
      .catch((err) => {
        handleAxiosError(err);
      })
      .finally(() => setLoading(false));
  }

  return (
    <>
      <Input
        name="email"
        type="email"
        value={email}
        onChange={(ev) => setEmail(ev.target.value)}
      />
      <Input
        name="password"
        type="password"
        value={password}
        onChange={(ev) => setPassword(ev.target.value)}
      />
      <Button disabled={!email || !password} loading={loading} onClick={login}>
        Login
      </Button>
    </>
  );
}
