import { Button, Input, InputNumber, message, Skeleton } from "antd";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  getProductSectionDefault,
  type ProductSectionDto,
} from "../../domain/ProductSectionDto";
import ProductApi from "../../api/ProductApi";
import { handleAxiosError } from "../../services/ErrorService";
import ProductSection from "./ProductSection";

const { TextArea } = Input;

export default function ProductDetails() {
  const [id, setId] = useState("");
  const [name, setName] = useState("");
  const [url, setUrl] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState(null as number | null);
  const [sections, setSections] = useState([] as ProductSectionDto[]);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);

  const { productId } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (!!productId) {
      setLoading(true);
      setId(productId);
      ProductApi.get(productId)
        .then((resp) => {
          setName(resp.data.name);
          setUrl(resp.data.url);
          setDescription(resp.data.description);
          setPrice(resp.data.price);
          const data = JSON.parse(
            resp.data.data || "[]",
          ) as ProductSectionDto[];
          setSections(data);
        })
        .catch((err) => {
          handleAxiosError(err);
        })
        .finally(() => {
          setLoading(false);
        });
    } else {
      ProductApi.create().then((resp) => {
        setId(resp.data.id);
        navigate(`/products/${resp.data.id}`, { replace: true });
      });
    }
  }, [productId]);

  function addSection() {
    setSections([...sections, { ...getProductSectionDefault() }]);
  }

  function removeSection(section: ProductSectionDto) {
    setSections(sections.filter((it) => it.id !== section.id));
  }

  function updateSection(updatedSection: ProductSectionDto) {
    setSections(
      sections.map((section) =>
        section.id === updatedSection.id ? updatedSection : section,
      ),
    );
  }

  function save() {
    setSaving(true);
    ProductApi.update(id, {
      name,
      description,
      price: price ?? 0,
      url,
      data: JSON.stringify(sections),
    })
      .then(() => {
        message.success({ content: "Changes saved!", duration: 3 });
      })
      .catch((err) => {
        handleAxiosError(err);
      })
      .finally(() => {
        setSaving(false);
      });
  }

  function publish() {}

  function preview() {
    window.location.href = `/c/${url}`;
  }

  return (
    <>
      {loading && <Skeleton active />}
      {!loading && (
        <div>
          <header>
            <nav>
              <Button loading={saving} onClick={() => preview()}>
                Preview
              </Button>

              <Button loading={saving} onClick={() => save()}>
                Save
              </Button>

              <Button loading={saving} onClick={() => publish()}>
                Publish
              </Button>
            </nav>
          </header>
          <Input
            size="large"
            placeholder="Product Name"
            value={name}
            onChange={(ev) => setName(ev.target.value)}
          />

          <Input
            size="large"
            placeholder="Public Url"
            value={url}
            onChange={(ev) => setUrl(ev.target.value)}
          />

          <InputNumber
            size="large"
            type="num"
            placeholder="Price"
            min={0}
            value={price}
            onChange={(val) => setPrice(val ?? 0)}
          />

          <TextArea
            size="large"
            placeholder="Product Description"
            value={description}
            onChange={(ev) => setDescription(ev.target.value)}
          />

          <div className="sections">
            <h3>Sections</h3>
            <ul>
              {sections.map((section) => (
                <ProductSection
                  key={section.id}
                  productId={id}
                  section={section}
                  updateSection={updateSection}
                  removeSection={removeSection}
                />
              ))}
            </ul>
            <Button onClick={addSection}>Add Section</Button>
          </div>
        </div>
      )}
    </>
  );
}
