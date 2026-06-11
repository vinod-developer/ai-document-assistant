import { useState } from "react";
import api from "../api/documentApi";
import Chip from "@mui/material/Chip";

import {
    Alert,
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Typography,
} from "@mui/material";

type Props = {
    onUploadSuccess: (documentId: string) => void;
};

function UploadDocument({ onUploadSuccess }: Props) {
    const [file, setFile] = useState<File | null>(null);

    const [message, setMessage] = useState("");

    const [documentId, setDocumentId] = useState("");

    const [loading, setLoading] = useState(false);

    const handleUpload = async () => {
        if (!file) {
            return;
        }

        const formData = new FormData();

        formData.append("file", file);

        setLoading(true);

        try {
            const response = await api.post("/api/documents/upload", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });

            const uploadedId = response.data.documentId;

            setDocumentId(uploadedId);

            setMessage(response.data.message);

            onUploadSuccess(uploadedId);
        } catch (error) {
            console.error(error);

            setMessage("Upload failed");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Card
            sx={{
                borderRadius: 3
            }}
        >
            <CardContent>
                <Typography
                    variant="h5"
                    gutterBottom
                    sx={{
                        textAlign: "center",
                    }}
                >
                    📄 Upload Document
                </Typography>

                <Box
                    sx={{
                        display: "flex",
                        flexDirection: "column",
                        gap: 2,
                    }}
                >
                    <Button variant="outlined" component="label">
                        Choose PDF
                        <input
                            hidden
                            type="file"
                            accept=".pdf"
                            onChange={(event) =>
                                setFile(event.target.files?.[0] || null)
                            }
                        />
                    </Button>

                    {file && (
                        <Chip
                            label={file.name}
                            color="primary"
                            variant="outlined"
                        />
                    )}

                    <Button
                        variant="contained"
                        size="large"
                        fullWidth
                        onClick={handleUpload}
                        disabled={loading}
                    >
                        {loading ? (
                            <CircularProgress size={24} color="inherit" />
                        ) : (
                            "Upload"
                        )}
                    </Button>

                    {message && <Alert severity="success">{message}</Alert>}

                    {documentId && (
                        <Alert severity="info">Document ID: {documentId}</Alert>
                    )}
                </Box>
            </CardContent>
        </Card>
    );
}

export default UploadDocument;
