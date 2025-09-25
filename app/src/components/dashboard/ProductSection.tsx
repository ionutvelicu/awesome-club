import { Button, Input, message, Popconfirm, Upload } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import Storage from "../../util/storage";
import { env } from "../../env/env";
import type { ProductSectionDto } from "../../domain/ProductSectionDto";
import type { ProductAssetTransporter } from "../../domain/ProductAssetTransporter.ts";

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
  const handleUploadAsset = ({ file }) => {
    const duration = 3;
    if (file.status === "done") {
      const sectionAssets = [...(JSON.parse(section.content) as ProductAssetTransporter[] || [])];
      sectionAssets.push({
        id: file.uid,
        filename: file.name,
        contentType: file.type,
        size: file.size,
        uploadedAt: new Date().toISOString(),
      } as ProductAssetTransporter);
      updateSection({ ...section, content: JSON.stringify(sectionAssets) });
      message.success({content: `${file.name} file uploaded successfully`, duration});
    }
    if (file.status === "error") {
      message.error({content: `${file.name} file upload failed.`, duration});
    }
  };
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
        onChange={handleUploadAsset}
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
