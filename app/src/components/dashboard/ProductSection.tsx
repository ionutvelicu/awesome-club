import { Button, Input, Popconfirm, Upload } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import Storage from "../../util/storage";
import { env } from "../../env/env";
import type { ProductSectionDto } from "../../domain/ProductSectionDto";

const { TextArea } = Input;

interface ProductSectionProps {
  productId: string;
  section: ProductSectionDto;
  updateSection: (section: ProductSectionDto) => void;
  removeSection: (section: ProductSectionDto) => void;
}

export default function ProductSection({
  productId,
  section,
  updateSection,
  removeSection,
}: ProductSectionProps) {
  return (
    <li>
      <Input
        placeholder="Section Name"
        value={section.title}
        onChange={(ev) => updateSection({ ...section, title: ev.target.value })}
      />

      <TextArea
        placeholder="Section Description"
        value={section.description}
        onChange={(ev) =>
          updateSection({ ...section, description: ev.target.value })
        }
      />

      <Upload
        name="content"
        action={`${env.apiPath}/products/${productId}/section/${section.id}/content`}
        headers={{
          Authorization: `Bearer ${Storage.getToken()}`,
        }}
      >
        <Button icon={<UploadOutlined />}>Upload Content</Button>
      </Upload>

      <Popconfirm
        placement="bottomLeft"
        title="Confirm?"
        onConfirm={() => removeSection(section)}
      >
        <button>x</button>
      </Popconfirm>
    </li>
  );
}
