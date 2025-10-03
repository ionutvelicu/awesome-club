export interface ProductAssetTransporter {
    id: string;
    filename: string;
    url: string;
    contentType: string;
    size: number;
    uploadedAt: string;
}