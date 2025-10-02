import { Button, Input, message, Popconfirm, Upload } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import Storage from "../../util/storage";
import { env } from "../../env/env";
import type { ProductSectionDto } from "../../domain/ProductSectionDto";
import type { ProductAssetTransporter } from "../../domain/ProductAssetTransporter.ts";
import { useEffect, useState } from "react";
import type { UploadFile } from "antd/es/upload/interface";
import { Constants } from "../../util/constants";
import ProductApi from "../../api/ProductApi";

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
  const [fileList, setFileList] = useState<UploadFile[]>([]);

  useEffect(() => {
    const existingAssets: ProductAssetTransporter[] = [
      ...(JSON.parse(section.content) as ProductAssetTransporter[] || []),
    ];
    console.log(existingAssets)
    const converted: UploadFile[] = existingAssets.map((asset) => ({
      uid: asset.id,
      name: asset.filename,
      size: asset.size,
      type: asset.contentType,
      status: "done"
    }));

    setFileList(converted);
  }, [section.content]);

  const handleUploadAsset = ({ file, fileList }) => {
    setFileList(fileList);

    if (file.status === "done") {
      const sectionAssets: ProductAssetTransporter[] = fileList.map((f) => ({
        id: f.uid,
        filename: f.name,
        contentType: f.type || "",
        size: f.size || 0,
        uploadedAt: new Date().toISOString(),
      }));
      updateSection({ ...section, content: JSON.stringify(sectionAssets) });
      message.success({ content: `${file.name} file uploaded successfully`, duration: Constants.MESSAGE_DURATION });
    }

    if (file.status === "error") {
      message.error({ content: `${file.name} file upload failed.`, duration: Constants.MESSAGE_DURATION });
    }
  };

  const handleRemoveAsset = async (file: UploadFile) => {
    const newFileList = fileList.filter((f) => f.uid !== file.uid);
    setFileList(newFileList);

    const updatedAssets: ProductAssetTransporter[] = newFileList.map((f) => ({
      id: f.uid,
      filename: f.name,
      contentType: f.type || "",
      size: f.size || 0,
      uploadedAt: new Date().toISOString(),
    }));
    updateSection({ ...section, content: JSON.stringify(updatedAssets) });

    try {
      // await ProductApi.deleteAssetFromSection(productId, section.id, );
      message.success({ content: `${file.name} deleted successfully`, duration: Constants.MESSAGE_DURATION });
    } catch (err) {
      message.error({ content: `Failed to delete ${file.name}`, duration: Constants.MESSAGE_DURATION });
    }

    return true;
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
        onRemove={handleRemoveAsset}
        fileList={fileList}
        listType="text"
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
