/*
 * Copyright (C) 2016 Jecelyin Peng <jecelyin@gmail.com>
 *
 * This file is part of 920 Text Editor.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jecelyin.android.file_explorer.io;

import android.os.Parcel;

import java.io.File;

/**
 * @author Jecelyin Peng <jecelyin@gmail.com>
 */
public class LocalFile extends JecFile {
    private File file;

    public LocalFile(JecFile parent, String child) {
        super(parent, child);
        file = new File(parent.getPath(), child);
    }

    public LocalFile(String parent, String child) {
        super(parent, child);
        file = new File(parent, child);
    }

    public LocalFile(String pathname) {
        super(pathname);
        file = new File(pathname);
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getParent() {
        return file.getParent();
    }

    @Override
    public JecFile getParentFile() {
        String parent = file.getParent();
        if (parent == null)
            return null;
        return new LocalFile(parent);
    }

    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    @Override
    public JecFile getAbsoluteFile() {
        return new LocalFile(file.getAbsoluteFile().getPath());
    }

    @Override
    public boolean canRead() {
        return file.canRead();
    }

    @Override
    public boolean canWrite() {
        return file.canWrite();
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    @Override
    public boolean isFile() {
        return file.isFile();
    }

    @Override
    public long lastModified() {
        return file.lastModified();
    }

    @Override
    public long length() {
        return file.length();
    }

    @Override
    public void delete(BoolResultListener listener) {
        boolean result = file.delete();
        if (listener != null)
            listener.onResult(result);
    }

    @Override
    public void listFiles(FileListResultListener listener) {
        if (listener == null)
            throw new NullPointerException();

        File[] files = file.listFiles();

        LocalFile[] localFiles = new LocalFile[files.length];
        for (int i = 0; i < files.length; i++) {
            localFiles[i] = new LocalFile(files[i].getPath());
        }

        listener.onResult(localFiles);
    }

    @Override
    public void mkdirs(BoolResultListener listener) {
        boolean result = file.mkdirs();

        if (listener != null)
            listener.onResult(result);
    }

    @Override
    public void renameTo(JecFile dest, BoolResultListener listener) {
        boolean result = file.renameTo(new File(dest.getPath()));
        if (listener != null)
            listener.onResult(result);
    }

    @Override
    public void upload(LocalFile file, BoolResultListener resultListener, ProgressUpdateListener progressUpdateListener) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(file.getPath());
    }

    protected LocalFile(Parcel in) {
        this(in.readString());
    }

    public static final Creator<LocalFile> CREATOR = new Creator<LocalFile>() {
        @Override
        public LocalFile createFromParcel(Parcel source) {
            return new LocalFile(source);
        }

        @Override
        public LocalFile[] newArray(int size) {
            return new LocalFile[size];
        }
    };
}
