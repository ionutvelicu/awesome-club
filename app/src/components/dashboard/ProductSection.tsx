import {
  Button,
  Dropdown,
  Input,
  type MenuProps,
  message,
  Upload,
} from "antd";
import Storage from "../../util/storage";
import { env } from "../../env/env";
import type { ProductSectionDto } from "../../domain/ProductSectionDto";
import type { ProductAssetTransporter } from "../../domain/ProductAssetTransporter.ts";
import { useEffect, useState } from "react";
import type { UploadFile } from "antd/es/upload/interface";
import { Constants } from "../../util/constants";
import { MenuIcon } from "../icons/MenuIcon.tsx";
import { DeleteIcon } from "../icons/DeleteIcon.tsx";
import { UploadIcon } from "../icons/UploadIcon.tsx";

const { TextArea } = Input;

interface ProductSectionProps {
  productId: string;
  index: number;
  section: ProductSectionDto;
  updateSection: (section: ProductSectionDto) => void;
  removeSection: (section: ProductSectionDto) => void;
}

export default function ProductSection({
  productId,
  section,
  index,
  updateSection,
  removeSection,
}: ProductSectionProps) {
  const [fileList, setFileList] = useState<UploadFile[]>([]);

  useEffect(() => {
    const existingAssets =
      (JSON.parse(section.content) as ProductAssetTransporter[]) || [];

    const converted: UploadFile[] = existingAssets.map((asset) => ({
      uid: asset.id,
      name: asset.filename,
      size: asset.size,
      type: asset.contentType,
      status: "done",
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
      message.success({
        content: `${file.name} file uploaded successfully`,
        duration: Constants.MESSAGE_DURATION,
      });
    }

    if (file.status === "error") {
      message.error({
        content: `${file.name} file upload failed.`,
        duration: Constants.MESSAGE_DURATION,
      });
    }
  };

  async function handleRemoveAsset(file: UploadFile) {
    const newFileList = fileList.filter((f) => f.uid !== file.uid);
    setFileList(newFileList);

    const updatedAssets: ProductAssetTransporter[] = newFileList.map((f) => ({
      id: f.uid,
      filename: f.name,
      contentType: f.type || "",
      size: f.size || 0,
      uploadedAt: new Date().toISOString(),
      url: "",
    }));
    updateSection({ ...section, content: JSON.stringify(updatedAssets) });

    try {
      // await ProductApi.deleteAssetFromSection(productId, section.id, );
      message.success({
        content: `${file.name} deleted successfully`,
        duration: Constants.MESSAGE_DURATION,
      });
    } catch (err) {
      message.error({
        content: `Failed to delete ${file.name}`,
        duration: Constants.MESSAGE_DURATION,
      });
    }

    return true;
  }

  function onDropdownClick(info: { key: string }) {
    console.log("dropdown clicked", info);
  }

  const items: MenuProps["items"] = [
    {
      label: (
        <a>
          <DeleteIcon />
          <span style={{position: "relative", top: "2px"}}>Delete Section</span>
        </a>
      ),
      key: "logout",
    },
  ];

  return (
    <div className={"section"}>
      <header>
        <h6>Section {index + 1}</h6>

        <nav>
          <Dropdown
            menu={{ items, onClick: onDropdownClick }}
            trigger={["click"]}
          >
            <Button type={"link"} className="act-btn">
              <MenuIcon />
            </Button>
          </Dropdown>
        </nav>
      </header>
      <div className={"block"}>
        <div className={"split"}>
          <div className={"two"}>
            <div className={"fm-item"}>
              <label>Name</label>
              <Input
                placeholder="Section Name"
                value={section.title}
                onChange={(ev) =>
                  updateSection({ ...section, title: ev.target.value })
                }
              />
            </div>

            <div className={"fm-item"}>
              <label>Description</label>
              <TextArea
                placeholder="Section Description"
                value={section.description}
                onChange={(ev) =>
                  updateSection({ ...section, description: ev.target.value })
                }
              />
            </div>
          </div>

          <div className={"one"}>
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
              <Button block icon={<UploadIcon />}>Upload Content</Button>
            </Upload>
          </div>
        </div>
      </div>
    </div>
  );
}
