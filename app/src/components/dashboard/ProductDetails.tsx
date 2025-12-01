import { Button, Input, InputNumber, message, Skeleton, Space } from "antd";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  getProductSectionDefault,
  type ProductSectionDto,
} from "../../domain/ProductSectionDto";
import ProductApi from "../../api/ProductApi";
import { handleAxiosError } from "../../services/ErrorService";
import ProductSection from "./ProductSection";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import { QUILL_FORMATS, QUILL_MODULES } from "../../services/QuillService.ts";

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
    <div className="product-details">
      {loading && (
        <div className={"app-sec-load"}>
          <Skeleton loading active />
        </div>
      )}
      {!loading && (
        <div>
          <header className={"dash"}>
            <h1>{name || "New product"}</h1>
            <nav>
              <Button loading={saving} onClick={() => preview()}>
                Preview
              </Button>

              <Button loading={saving} onClick={() => save()}>
                Save
              </Button>

              <Button
                size="large"
                type={"primary"}
                loading={saving}
                onClick={() => publish()}
              >
                Publish
              </Button>
            </nav>
          </header>

          <div className="content">
            <div className="block">
              <div className="split">
                <div className={"one"}>
                  <div className={"fm-item"}>
                    <label>Name</label>
                    <Input
                      size="large"
                      placeholder="Product Name"
                      value={name}
                      onChange={(ev) => setName(ev.target.value)}
                    />
                  </div>

                  <div className={"fm-item"}>
                    <label>Public URL</label>
                    <Space.Compact>
                      <Space.Addon>awesome.club/p/</Space.Addon>
                      <Input
                        size="large"
                        placeholder="Public Url"
                        value={url}
                        onChange={(ev) => setUrl(ev.target.value)}
                      />
                    </Space.Compact>
                  </div>

                  <div className={"fm-item"}>
                    <label>Price</label>
                    <Space.Compact>
                      <Space.Addon>$</Space.Addon>
                      <InputNumber
                        size="large"
                        type="num"
                        placeholder="Price"
                        min={0}
                        value={price}
                        onChange={(val) => setPrice(val ?? 0)}
                      />
                    </Space.Compact>
                  </div>
                </div>
                <div className={"one-half"}>
                  <div className={"fm-item"}>
                    <label>Details</label>
                    <ReactQuill
                      theme="snow"
                      value={description}
                      onChange={setDescription}
                      modules={QUILL_MODULES}
                      formats={QUILL_FORMATS}
                    />
                  </div>
                </div>
              </div>
            </div>

            <div className="sections">
              <ul>
                {sections.map((section, idx) => (
                  <li key={section.id}>
                    <ProductSection
                      index={idx}
                      productId={id}
                      section={section}
                      updateSection={updateSection}
                      removeSection={removeSection}
                    />
                  </li>
                ))}
              </ul>
              <Button className={"mt"} size={"large"} block onClick={addSection}>+ Section</Button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
