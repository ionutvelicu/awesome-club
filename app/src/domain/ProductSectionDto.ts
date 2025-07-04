export interface ProductSectionDto {
  id: string;
  sequence: number;
  title: string;
  description: string;
  content: string;
  minutes: number;
  seconds: number;
}

export function getProductSectionDefault() {
  return {
    id: crypto.randomUUID(),
    sequence: 0,
    title: "",
    description: "",
    content: "[]",
    minutes: 0,
    seconds: 0,
  };
}
