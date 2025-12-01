import { useEffect, useState } from "react";
import ProductApi from "../../api/ProductApi";
import type { ProductDto } from "../../generated/api-client";
import { handleAxiosError } from "../../services/ErrorService";
import { Button, Empty, Popconfirm, Skeleton } from "antd";
import { useNavigate } from "react-router-dom";

export default function Products() {
  const [products, setProducts] = useState([] as ProductDto[]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    ProductApi.getForAuthor()
      .then((resp) => {
        setProducts(resp.data);
      })
      .catch((err) => {
        handleAxiosError(err);
      })
      .finally(() => setLoading(false));
  }, []);

  function addNew() {
    navigate("/products/new");
  }

  function edit(product: ProductDto) {
    navigate(`/products/${product.id}`);
  }

  function removeProduct(product: ProductDto) {
    ProductApi.delete(product.id)
      .then(() => {
        setProducts(products.filter((it) => it.id !== product.id));
      })
      .catch((err) => {
        handleAxiosError(err);
      });
  }

  return (
    <div className="products">
      <header className={"dash"}>
        <h1>Your Products</h1>
        <nav>
          <Button type={"primary"} size={"large"} onClick={addNew}>
            + New Product
          </Button>
        </nav>
      </header>

      <div className="content">
        {loading && <Skeleton loading active />}

        {!loading && products.length > 0 && (
          <>
            <ul>
              {products.map((product) => (
                <li key={product.id}>
                  <h3 onClick={() => edit(product)}>
                    {product.name || "Unnamed Product"}
                  </h3>
                  <Popconfirm
                    placement="bottom"
                    title={"Confirm?"}
                    onConfirm={() => removeProduct(product)}
                  >
                    <button>x</button>
                  </Popconfirm>
                </li>
              ))}
            </ul>
          </>
        )}

        {!loading && products.length === 0 && (
          <div className="empty">
            <Empty description={null} />
            <p>You don't have any published digital products yet.</p>
          </div>
        )}
      </div>
    </div>
  );
}
