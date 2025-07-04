import type { AxiosError } from "axios";
import { message } from "antd";

const MSG: { [key: string]: string } = {
  "email.already.registered": "Email in use.",
  "email.pass.incorrect": "Incorrect email or password.",
};

export function handleAxiosError(err: AxiosError) {
  let content = "Something went wrong. Please retry or contact us.";
  if ((err.response?.data as any).message) {
    const key = (err.response?.data as any).message as string;
    content = MSG[key] ?? content;
  }
  console.error(content);
  message.error({ content, duration: 3 });
}
